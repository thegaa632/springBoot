async function uploadToServer (formObj) {
    console.log("upload to server!")
    console.log(formObj)

    const response = await ({
        method: 'post',
        url: '/upload',

    })
}