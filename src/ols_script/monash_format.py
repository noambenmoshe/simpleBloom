import pandas as pd
import numpy as np
import csv

input_file = 'ols.csv'

if __name__ == "__main__":
    mols = []
    with open('ols.csv', 'r') as file:
        reader = csv.reader(file)
        ols = []
        for row in reader:
            if not row:
                mols.append(ols)
                ols = []
                continue
            for word in row:
                # size = len(word)
                for letter in word:
                    number = ord(letter) - 96
                    ols.append(number)
        mols.append(ols)

    for i in range(len(mols)):
        size = np.max(mols[i])
        print(f'List<Integer>numList{size}_{i} = Arrays.asList(', end="")
        print(*mols[i], sep=',', end='')
        print(');')

    print('\nFor MOLS mols_data = new MOLS();')

    for i in range(len(mols)):
        #  mols_data.insert_ols(s, numList1);
        size = np.max(mols[i])
        print(f'mols_data.insert_ols({size}, numList{size}_{i});')
    print('Done')