import plotly.graph_objects as go
import math


def addToDict(dictionary, d, universeSize, bfSize):
    dDict = {
        'd': d,
        'universeSizeList': universeSize,
        'bfSize': bfSize
    }
    dictionary[d] = dDict


def createDictionary(type):
    dictionary = {}
    with open(f'{type}_res.txt') as fp:
        Lines = fp.read().splitlines()
        bfType = Lines.pop(0)
        assert(type == bfType)
        d = int(Lines.pop(0))
        universeSize = []
        bfSize = []
        for line in Lines:
            # print("Line = {}".format(line.strip()))
            if len(line) == 1:
                addToDict(dictionary, d, universeSize, bfSize)
                d = int(line)
                universeSize = []
                bfSize = []
            else:
                assert(d is not None)
                x, y = line.split(',')
                universeSize.append(int(x))
                bfSize.append(int(y))
        addToDict(dictionary, d, universeSize, bfSize)
        return dictionary


def plotVecSizeByUniSize(d):
    fig = go.Figure()
    fig.add_trace(
        go.Scatter(x=[math.log(x, 2) for x in pol_dict[d]['universeSizeList']], y=pol_dict[d]['bfSize'], name='POL',
                   line=dict(color='firebrick', width=4)))
    fig.add_trace(
        go.Scatter(x=[math.log(x, 2) for x in ols_dict[d]['universeSizeList']], y=ols_dict[d]['bfSize'], name='OLS',
                   line=dict(color='royalblue', width=4, dash='dash')))
    fig.update_layout(title=f'Maximal Set Size d={d}',
                      xaxis_title=r"$\text{Universe Size } 2^{x}$",
                      yaxis_title=r"$\text{Filter Length [bits]}$")
    fig.show()


if __name__ == "__main__":
    pol_dict = createDictionary("POL")
    ols_dict = createDictionary("OLS")

    for key in pol_dict:
        if key in ols_dict:
            plotVecSizeByUniSize(key)

    print('Done.')


