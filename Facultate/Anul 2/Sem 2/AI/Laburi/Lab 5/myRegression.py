class MyLinearUnivariateRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = 0.0
    def fit(self, x, y):
        sx = sum(x)
        sy = sum(y)
        sx2 = sum(i * i for i in x)
        sxy = sum(i * j for (i,j) in zip(x, y))
        w1 = (len(x) * sxy - sx * sy) / (len(x) * sx2 - sx * sx)
        w0 = (sy - w1 * sx) / len(x)
        self.intercept_, self.coef_ =  w0, w1
    def predict(self, x):
        if (isinstance(x[0], list)):
            return [self.intercept_ + self.coef_ * val[0] for val in x]
        else:
            return [self.intercept_ + self.coef_ * val for val in x]

class MyLinearBivariateRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = [0.0, 0.0]

    def fit(self, x, y):
        n = len(y)
        sx1 = sum(row[0] for row in x)
        sx2 = sum(row[1] for row in x)
        sy = sum(y)
        sx1x1 = sum(row[0] * row[0] for row in x)
        sx2x2 = sum(row[1] * row[1] for row in x)
        sx1x2 = sum(row[0] * row[1] for row in x)
        sx1y = sum(row[0] * yi for row, yi in zip(x, y))
        sx2y = sum(row[1] * yi for row, yi in zip(x, y))
        A = [
            [n, sx1, sx2],
            [sx1, sx1x1, sx1x2],
            [sx2, sx1x2, sx2x2]
        ]
        b = [sy, sx1y, sx2y]

        def solve3(A, b):
            import copy
            M = [row[:] + [bv] for row, bv in zip(copy.deepcopy(A), b)]
            for col in range(3):
                pivot = max(range(col, 3), key=lambda r: abs(M[r][col]))
                M[col], M[pivot] = M[pivot], M[col]
                for row in range(col + 1, 3):
                    if M[col][col] == 0:
                        continue
                    f = M[row][col] / M[col][col]
                    for k in range(col, 4):
                        M[row][k] -= f * M[col][k]
            sol = [0.0] * 3
            for i in range(2, -1, -1):
                sol[i] = M[i][3]
                for j in range(i + 1, 3):
                    sol[i] -= M[i][j] * sol[j]
                sol[i] /= M[i][i]
            return sol

        w0, w1, w2 = solve3(A, b)
        self.intercept_ = w0
        self.coef_ = [w1, w2]

    def predict(self, x):
        return [self.intercept_ + self.coef_[0] * row[0] + self.coef_[1] * row[1] for row in x]

def myMAE(realOut, predOut):
    return sum(abs(r - p) for r, p in zip(realOut, predOut)) / len(realOut)


def myMSE(realOut, predOut):
    return sum((r - p) ** 2 for r, p in zip(realOut, predOut)) / len(realOut)