var fs = require('fs');

var inputFile = 'test/data/sample.txt';

// read synchronously
module.exports.readFileSync = function (filename) {
    var syncContent = fs.readFileSync(inputFile).toString();
    console.log("syncContent is " + syncContent);
    return syncContent;
}


// read asynchronously
module.exports.readFileAsync = function (filename) {
    fs.readFile(inputFile, function (err, buf) {
        var asyncContent = buf.toString();
        console.log("asyncContent is " + asyncContent);
        return asyncContent;
    })
}

this.readFileSync(inputFile);
this.readFileAsync(inputFile);