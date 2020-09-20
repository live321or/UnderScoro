from re import fullmatch, match

if __name__ == '__main__':
    uname = "Gdsfw#esdvd#$$"
    res = {}
    if not fullmatch(r'^[A-Za-zА-Яа-я0-9-_]+$', uname):
        m = match(r'^[A-Za-zА-Яа-я0-9-_]+', uname)

        res['symbol'] = {
            "message": "Username contains unexpected symbol",
            'symb': uname[m.end(0)],
            'position': m.end(0)
        }
        print(res)