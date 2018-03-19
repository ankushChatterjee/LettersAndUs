const functions = require('firebase-functions');          
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


exports.makeUppercase = functions.database.ref('/messages/{userId}/{pushId}')
.onWrite(event => {
    const letter = event.data.val();
    const pushId = event.params.pushId;
    const userId = event.params.userId;
    const payload = {
        notification: {
        title: `Letter from ${letter.from}`,
        body: 'New Letter',
        },
        data:{
            url:letter.stampUrl,
            text:letter.letterText,
            favourited:"true"
        }
    };
    let id = "";
    if(letter.from === "u1")
    {
        console.log("got u1");
        id = "u2";
    }else{
        console.log("got u2");
        id = "u1";
    }
    const refString = "/userData/"+id+"/notificationKey/";
    console.log(refString);
    return admin.database().ref(refString).once('value').then(notificationKey => {
        console.log(notificationKey)
        if (notificationKey.val()) {
            console.log(notificationKey.val())
          return admin.messaging().sendToDevice(notificationKey.val(), payload);
        }
      });
});
