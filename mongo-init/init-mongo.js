// init-mongo.js

db = db.getSiblingDB('bezkoder_db'); // Sélectionner ou créer la base de données

// Insérer les rôles
db.roles.insertMany([
  {
    _id: ObjectId("669e2f9573f490c35507ee72"),
    name: "ROLE_USER"
  },
  {
    _id: ObjectId("669e2fbb73f490c35507ee73"),
    name: "ROLE_MODERATOR"
  },
  {
    _id: ObjectId("669e2fd573f490c35507ee75"),
    name: "ROLE_ADMIN"
  }
]);

// Insérer l'utilisateur
db.users.insertOne({
  _id: ObjectId("669e6ad721568616be631334"),
  username: "mmillox",
  email: "mmillox@gmail.com",
  password: "$2a$10$zhwe61v8BFJsTO9MK5GyQ.AMuITZje22h4226QLqH6WF0883Zlbr6",
  roles: [
    {
      $ref: "roles",
      $id: ObjectId("669e2f9573f490c35507ee72")
    },
    {
      $ref: "roles",
      $id: ObjectId("669e2fd573f490c35507ee75")
    },
    {
      $ref: "roles",
      $id: ObjectId("669e2fbb73f490c35507ee73")
    }
  ],
  _class: "com.service.auth.models.User"
});
