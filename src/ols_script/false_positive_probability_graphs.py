import plotly.graph_objects as go



def addToDict(dictionary, d, setSize, fpPercent):
    dDict = {
        'd': d,
        'setSizeList': setSize,
        'fpPercent': fpPercent
    }
    dictionary[d] = dDict


def createDictionary(type):
    dictionary = {}
    with open(f'{type}_FP_res.txt') as fp:
        Lines = fp.read().splitlines()
        bfType = Lines.pop(0)
        assert(type == bfType)
        d = int(Lines.pop(0))
        setSize = []
        fpPercent = []
        for line in Lines:
            # print("Line = {}".format(line.strip()))
            if len(line) == 1:
                addToDict(dictionary, d, setSize, fpPercent)
                d = int(line)
                setSize = []
                fpPercent = []
            else:
                assert(d is not None)
                x, y = line.split(',')
                setSize.append(int(x))
                fpPercent.append(y)
        addToDict(dictionary, d, setSize, fpPercent)
        return dictionary


def plotVecSizeByUniSize(d):
    fig = go.Figure()
    fig.add_trace(
        go.Scatter(x=[pol_dict[d]['setSizeList']], y=pol_dict[d]['fpPercent'], name='POL',
                   line=dict(color='firebrick', width=4)))
    fig.add_trace(
        go.Scatter(x=[ols_dict[d]['setSizeList']], y=ols_dict[d]['fpPercent'], name='OLS',
                   line=dict(color='royalblue', width=4, dash='dash')))
    fig.update_layout(title=f'Maximal FPFZ Set Size d={d}',
                      xaxis_title=r"$\text{Set Size } 2^{x}$",
                      yaxis_title=r"$\text{False Positive Percent}$")
    fig.show()


if __name__ == "__main__":
    pol_dict = createDictionary("POL")
    ols_dict = createDictionary("OLS")

    for key in pol_dict:
        if key in ols_dict:
            plotVecSizeByUniSize(key)

    print('Done.')