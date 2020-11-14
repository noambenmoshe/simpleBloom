import plotly.graph_objects as go
from scipy.optimize import curve_fit
import numpy as np


def probability_fit(x, a, b, c):  # , d):
    return a * pow(x, 3) + b * pow(x, 2) + c * x  # + d


def addToDict(dictionary, d, setSize, fpPercent):
    dDict = {
        'd': d,
        'setSizeList': setSize,
        'fpPercent': fpPercent
    }
    dictionary[d] = dDict


def createDictionary(type):
    dictionary = {}
    with open(f'output_for_graphs_{type}_FP_01.txt') as fp:
        Lines = fp.read().splitlines()
        bfType = Lines.pop(0)
        assert(type == bfType)
        d = int(Lines.pop(0)[4])
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
    # POL
    fig.add_trace(go.Scatter(x=pol_dict[d]['setSizeList'], y=pol_dict[d]['fpPercent'], name='POL', mode='markers',
                             line=dict(color='firebrick')))
    popt, pcov = curve_fit(probability_fit, pol_dict[d]['setSizeList'], pol_dict[d]['fpPercent'])
    y_fit_vals = probability_fit(np.array(pol_dict[d]['setSizeList']), *popt)
    fig.add_trace(go.Scatter(x=pol_dict[d]['setSizeList'], y=y_fit_vals, line_shape='spline',
                  line=dict(color='firebrick', width=2), name='POL fit'))
    # OLS
    fig.add_trace(go.Scatter(x=ols_dict[d]['setSizeList'], y=ols_dict[d]['fpPercent'], name='OLS', mode='markers',
                             line=dict(color='royalblue')))
    popt, pcov = curve_fit(probability_fit, ols_dict[d]['setSizeList'], ols_dict[d]['fpPercent'])
    y_fit_vals = probability_fit(np.array(ols_dict[d]['setSizeList']), *popt)
    fig.add_trace(go.Scatter(x=pol_dict[d]['setSizeList'], y=y_fit_vals, line_shape='spline',
                             line=dict(color='royalblue', width=2, dash='dash'), name='OLS fit'))
    # TITLE
    fig.update_layout(title=f'False Positive Probability',
                      xaxis_title=r"$\text{Set Size}$",
                      yaxis_title=r"$\text{Probability}$")
    fig.show()


if __name__ == "__main__":
    pol_dict = createDictionary("POL")
    ols_dict = createDictionary("OLS")

    for key in pol_dict:
        if key in ols_dict:
            plotVecSizeByUniSize(key)

    print('Done.')