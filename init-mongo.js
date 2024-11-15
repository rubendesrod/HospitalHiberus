// init-mongo.js
db = db.getSiblingDB('historialmedico_db');

db.createUser({
  user: 'historialmedico_user',
  pwd: 'historialmedico_password',
  roles: [
    {
      role: 'readWrite',
      db: 'historialmedico_db',
    },
  ],
});

print("User historialmedico_user created with readWrite role on historialmedico_db database.");
