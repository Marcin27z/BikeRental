import bcrypt

password = "ala ma kota".encode("utf-8")
hashed_pwd = bcrypt.hashpw(password, bcrypt.gensalt())
hashed_pwd = hashed_pwd.decode("utf-8")

h_test = "$2b$12$yolNhbeUodLz/rLPPBoNVOliPadJS82YChwbRGjbdth2WbmEj34nW"
p_test = "cerise-polecat".encode("utf-8")

print(hashed_pwd)
print(bcrypt.checkpw(p_test, h_test.encode("utf-8")))