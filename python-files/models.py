from app import db
from passlib.apps import custom_app_context as pwd_context

class Users(db.Model):
     __tablename__ = "users"
     u_id = db.Column(db.Integer, primary_key=True)
     username = db.Column(db.String(80))
     passhash = db.Column(db.String(256))
     fname = db.Column(db.String(80))
     lname = db.Column(db.String(80))

     def hash_password(self, password):
         self.passhash = pwd_context.encrypt(password)

     def verify_password(self, password):
         return pwd_context.verify(password, self.passhash)
     
     @property
     def is_active(self):
         return True

     @property
     def is_authenticated(self):
         return True

     @property
     def is_anonymous(self):
         return False

     def get_id(self):        
         return str(self.u_id)


class resting_health_info(db.Model):
     __tablename__ = "resting_health_info"
     r_id = db.Column(db.Integer, primary_key=True)
     u_id = db.Column(db.Integer)
     disease = db.Column(db.String(40))
     BS_AR = db.Column(db.Integer)
     HR_AR = db.Column(db.Integer)
     systolic_AR = db.Column(db.Integer)
     diastolic_AR = db.Column(db.Integer)
     is_baseline = db.Column(db.Boolean)
     accel_x = db.Column(db.Integer)
     accel_y = db.Column(db.Integer)
     accel_z = db.Column(db.Integer)

class active_health_info(db.Model):
    __tablename__ = "active_health_info"
    r_id = db.Column(db.Integer, primary_key=True)
    u_id = db.Column(db.Integer)
    disease = db.Column(db.String(40))
    BS_ACC = db.Column(db.Integer)
    HR_ACC = db.Column(db.Integer)
    systolic_ACC = db.Column(db.Integer)
    diastolic_ACC = db.Column(db.Integer)
    is_baseline = db.Column(db.Boolean)
    accel_x = db.Column(db.Integer)
    accel_y = db.Column(db.Integer)
    accel_z = db.Column(db.Integer)

class deq_keys(db.Model):
    __tablename__ = "deq_keys"
    k_id = db.Column(db.Integer, primary_key=True)
    r_id = db.Column(db.Integer)
    u_id = db.Column(db.Integer)
    key = db.Column(db.String(256))

class deq_survey(db.Model):
    __tablename__ = "deq_survey"
    r_id = db.Column(db.Integer, primary_key=True)
    u_id = db.Column(db.Integer)
    results = db.Column(db.String(256))
    is_baseline = db.Column(db.Boolean)






db.create_all()
