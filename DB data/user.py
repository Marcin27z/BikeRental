import string
import coolname
import random
import bcrypt

def random_mail():
    domains = ['hotmail.com', 'gmail.com', 'yahoo.com', 'mymail.com', 'myspace.com', 'euromail.eu']
    mail = ''
    mail += coolname.generate_slug()
    mail += '@' + random.choice(domains)
    return mail


def random_phone():
    phoneNumber = ''
    for i in range(1, 10):
        phoneNumber += random.choice(string.digits)
    return phoneNumber

filename = "Insert_User.txt"
credentials_filename = "credentials.txt"  # zawiera login + plaintextowe hasło + zahashowane hasło
file = open(filename, "w")
credentials_file = open(credentials_filename, "w")
insert_string = "INSERT INTO public.users(user_id, email, phone_number, is_admin, password, login, token, is_active) "


for i in range(0, 1000):
    print(i)
    user_id = "DEFAULT"
    email = "\'" + random_mail() + "\'"
    phone_number = "\'" + random_phone() + "\'"
    is_admin = "false"
    pwd_to_hash = coolname.generate_slug(2).encode("utf-8")
    password = "\'" + bcrypt.hashpw(pwd_to_hash, bcrypt.gensalt()).decode("utf-8") + "\'"
    login = "\'" + coolname.generate_slug(2) + "\'"
    token = "DEFAULT"
    is_active = "true"
    values_string = "VALUES(%s, %s, %s, %s, %s, %s, %s, %s);\n" % (user_id, email, phone_number, is_admin, password, login, token, is_active )
    file.write(insert_string + values_string)
    credentials_file.write(login + "\t" + pwd_to_hash.decode("utf-8") + "\t" + password + "\n")

file.close()
credentials_file.close()