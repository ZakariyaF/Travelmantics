# Travelmantics
> ALC 4 Phase 1 Challenge 2 Android application.

_Note: In order for the project to build and run properly, please add your own google-services.json file to the app folder._

## Firebase Database rules:
```
{
  "rules": {
    ".read": "auth != null",
    ".write": "root.child('administrators').hasChild(auth.uid)"
  }
}
```
![Example screenshot](./app/firebase_sc.png)
## Firebase Storage rules:
```
rules_version = '2';
function isAdmin(){
return request.auth.uid in{
"insert your admin uid here" : "insert your admin name here"
};
}

service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && isAdmin();
    }
  }
}
```

