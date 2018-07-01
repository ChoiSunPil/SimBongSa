var express = require('express')
var bodyParser = require('body-parser')
var router = require('./router/index')
var app = express()
app.listen(3000, function(){
  console.log("start! SimBongSa server on 3000")
})
app.use(express.static('public'));
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended:true}))
app.use(router)
