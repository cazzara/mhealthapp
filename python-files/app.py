from flask import Flask, flash, redirect, request, session, abort, jsonify, g, url_for
import ssl
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager, login_required, login_user, current_user, logout_user
import hashlib
import os
from Crypto.Cipher import AES
from Crypto import Random
import base64

context = ssl.SSLContext(ssl.PROTOCOL_SSLv23)
context.options |= ssl.OP_NO_SSLv2
context.options |= ssl.OP_NO_SSLv3
context.options |= ssl.OP_NO_TLSv1
context.options |= ssl.OP_NO_TLSv1_1
#context.set_ciphers("ECDHE-ECDSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-CBC-SHA384:ECDHE-RSA-AES256-CBC-SHA256:ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES128-GCM-SHA256:DHE-RSA-AES128-GCM-SHA256:DHE-RSA-AES256-GCM-SHA384:!aNULL:!MD5:!DSS")
context.load_cert_chain('cert.crt', 'privkey.pem')
context.load_default_certs()
app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = "db_connection_goes_here"
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
app.config['SECRET_KEY'] = "secret_key_goes_here"

login_manager = LoginManager()
login_manager.init_app(app)

master = hashlib.pbkdf2_hmac('sha256', b'password', b'salt', 100000, 32)

@login_manager.user_loader
def load_user(user_id):
    return models.Users.query.filter_by(u_id =int(user_id)).first()

db = SQLAlchemy(app)

import models



@app.route('/test')
@login_required
def index():
    return "Hello World!"

@app.route('/register', methods=['POST'])
def register_user():
    username = request.form['username']
    password = request.form['password']
    fname = request.form['fname']
    lname = request.form['lname']
    if username is None or password is None:
        return jsonify({'success':False})
    if models.Users.query.filter_by(username = username).first() is not None:
        return jsonify({'success':False})
    new_user = models.Users(username = username, fname = fname, lname = lname)
    new_user.hash_password(password)
    db.session.add(new_user)
    db.session.commit()
    return jsonify({'username': new_user.username, 'success':True})
    
@app.route('/login', methods=['POST'])
def login():
    username = request.form['username']
    password = request.form['password']
    user = models.Users.query.filter_by(username = username).first()
    if not user or not user.verify_password(password):
        return jsonify({'success': False})
    login_user(user)
    print(current_user.u_id, current_user.username)
    return jsonify({'success': True})

@app.route('/submit-data-ar', methods=['POST', 'GET'])
@login_required
def ar_data():
    if request.method == 'GET':
        return "Hello Protected World!"
    else:
        print(current_user.u_id, current_user.username)        
        u_id = current_user.get_id()
        disease = request.form['disease']
        BS_AR = int(request.form['BS_AR'])
        HR_AR = int(request.form['HR_AR'])
        systolic_AR = int(request.form['systolic_AR'])
        diastolic_AR = int(request.form['diastolic_AR'])
        is_baseline = request.form['is_baseline']
        accel_x = int(request.form['accel_x'])
        accel_y = int(request.form['accel_y'])
        accel_z = int(request.form['accel_z'])
        new_health_record = models.resting_health_info(u_id=u_id, disease=disease, BS_AR=BS_AR, HR_AR=HR_AR, systolic_AR=systolic_AR, diastolic_AR=diastolic_AR, is_baseline=is_baseline, accel_x=accel_x, accel_y=accel_y, accel_z=accel_z)
    
        db.session.add(new_health_record)
        db.session.commit()
        return jsonify({'success':True})


@app.route('/submit-data-acc', methods=['POST', 'GET'])
@login_required
def acc_data():
    if request.method == 'GET':
        return "Hello Protected World!"
    else:
        u_id = current_user.get_id()
        disease = request.form['disease']
        BS_ACC = int(request.form['BS_ACC'])
        HR_ACC = int(request.form['HR_ACC'])
        systolic_ACC = int(request.form['systolic_ACC'])
        diastolic_ACC = int(request.form['diastolic_ACC'])
        is_baseline = request.form['is_baseline']
        accel_x = int(request.form['accel_x'])
        accel_y = int(request.form['accel_y'])
        accel_z = int(request.form['accel_z'])
        new_health_record = models.active_health_info(u_id=u_id, disease=disease, BS_ACC=BS_ACC, HR_ACC=HR_ACC, systolic_ACC=systolic_ACC, diastolic_ACC=diastolic_ACC, is_baseline=is_baseline, accel_x=accel_x, accel_y=accel_y, accel_z=accel_z)

        db.session.add(new_health_record)
        db.session.commit()
        return jsonify({'success':True})

@app.route('/submit-data-deq', methods=['POST'])
@login_required
def deq():
    iv = Random.new().read(AES.block_size)
    key = Random.new().read(32)
    cipher = AES.new(key, AES.MODE_CFB, iv)
    u_id = current_user.get_id()
    results = request.form['results']
    is_baseline = request.form['is_baseline']
    results = iv + cipher.encrypt(results)
    results64 = base64.b64encode(results)

    key_iv = Random.new().read(AES.block_size)
    key_cipher = AES.new(master, AES.MODE_CFB, key_iv)
    key = key_iv + key_cipher.encrypt(key)
    key64 = base64.b64encode(key)

    new_deq_survey = models.deq_survey(u_id = u_id, results=str(results64), is_baseline=is_baseline)
    db.session.add(new_deq_survey)
    db.session.commit()
    r_id = new_deq_survey.r_id
    deq_key  = models.deq_keys(r_id=r_id, u_id=u_id, key=str(key64))
    db.session.add(deq_key)
    db.session.commit()
    print(key, key64, results, results64)
    return jsonify({'success':True})

@app.route('/logout')
@login_required
def logout():
    if current_user:
        logout_user()
        return jsonify({'success':True})
    else:
        return jsonify({'success':False})
    
if __name__ == "__main__":
    app.run(host='0.0.0.0', ssl_context=context)



