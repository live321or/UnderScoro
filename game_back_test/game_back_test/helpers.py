from re import fullmatch, match


def check_username(uname):
    res = {}
    if len(uname) > 16 or len(uname) < 6:
        res['len'] = {
                        "message": "Length of the username is too big or less",
                        "value": len(uname)
                    }
    if not fullmatch(r'^[A-Za-zА-Яа-я0-9-_]+$', uname):
        m = match(r'^[A-Za-zА-Яа-я0-9-_]+', uname)

        res['symbol'] = {
            "message": "Username contains unexpected symbol",
            'symb': uname[m.end(0)],
            'position': m.end(0)
        }
    return res
    pass


def check_pass(passwd):
    res = {}
    if len(passwd) > 16 or len(passwd) < 8:
        res['len'] = {
            "message": "Length of the password is too big or less",
            "value": len(passwd)
        }
    if not fullmatch(r"^[A-Za-z0-9_!@#$%^&*(),.<>]+$", passwd):
        m = match(r"^[A-Za-z0-9_!@#$%^&*(),.<>]+", passwd)
        try:
            pos = m.end(0)
        except:
            pos = passwd[0]
        res['symbol'] = {
            "message": "Password contains unexpected symbol",
            'symb': passwd[pos],
            'position': pos
        }
    return res
    pass
