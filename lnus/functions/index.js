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
    if(letter.from === "ankush")
    {
        console.log("got popo");
        id = "popo";
    }else{
        console.log("got ankush");
        id = "ankush";
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
    // const notificationKey = 'fZzo_YU6U1k:APA91bFl6efqEsQsBCyP7wKEOCxGOFI4GOVz7TQtW_q5jIKfjR5qs5pO6hv3U0JFEviu3OgdCpBd6AjScsbIWcWqvGoyGFVw32CM-ZQgBWel3UNXRQlpkFbCSdfLQcEaKZ1SMV6k70Gu';
    // return admin.messaging().sendToDevice(notificationKey, payload);
});
