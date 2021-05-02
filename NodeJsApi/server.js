////Update 15/02/63 project Apprunner
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
extended: true
}));

app.listen(3000, function () {
    console.log('Node app is running on port 3000');
});

module.exports = app;

// default route
app.get('/', function (req, res) {
    return res.send({ error: true, message: 'Test Web API' })
    });
    
    // connection configurations
    var dbConn = mysql.createConnection({
        host: 'localhost',
        user: 'root', 
        password: '',
        database: 'data_running'
    });

// add event
app.post('/add_event', function (req,res){
    console.log("Add event")
    var data = req.body
    if (!data ) {
         return res.status(400).send({ error:true, message: 'Please Register Again' });
    }

    dbConn.query(`INSERT INTO add_event (id_user,
        pic_event,
        name_organizer,
        tel,
        name_event,
        name_producer,
        date_reg_start,
        date_reg_end,
        bank,
        num_bank,
        objective,
        detail) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)`, [req.body.id_user,
        req.body.pic_event,
        req.body.name_organizer,
        req.body.tel,
        req.body.name_event,
        req.body.name_producer,
        req.body.date_reg_start,
        req.body.date_reg_end,
        req.body.bank,
        req.body.num_bank,
        req.body.objective,
        req.body.detail], function (error, results, fields) {

        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json({"status":"ok"});
            res.end()
        }
    });
});
    
//login
app.get('/login/:email', function (req, res){
    console.log("login")
    let email = req.params.email;
    
    
    if (!email) {
        return res.status(400).send({ error: true, message: 'Please provide email'});
    }
   
        
        dbConn.query('SELECT * FROM user where email = ?', [email], function (error, results, fields) {
            if (error){
                console.log(error)
                res.status(422).json({"status":"failed"});
                res.end()
            }else{
                res.status(200).json(results[0]);
                res.end()
            }
        });

    });

app.get('/check_email/:email', function (req, res){
    console.log("login email")
    let email = req.params.email;
        
    if (!email) {
        return res.status(400).send({ error: true, message: 'Please provide email'});
    }   
        dbConn.query('SELECT * FROM user where email = ?', [email], function (error, results, fields) {
            if (error){
                console.log(error)
                res.status(422).json({"status":"failed"});
                res.end()
            }else{
                res.status(200).json(results[0]);
                res.end()
            }
        });
    
    });

//insert register
app.post('/register', function(req,res){
    console.log("register")
    var register = req.body

    if(!register){
        return res.status(400).send({error:true,message: 'Please provide student'});
    }
    dbConn.query("INSERT INTO user SET ?", register , function(error, results, fieldds){
        if (error) throw error;
        return res.send(results);
    });
});

//insert deail_distance
app.post('/detail_distance', function(req,res){
    console.log("detail_distance")
    var register = req.body

    if(!register){
        return res.status(400).send({error:true,message: 'Please provide distance'});
    }
    dbConn.query("INSERT INTO detail_distance SET ?", data , function(error, results, fieldds){
        if (error) throw error;
        return res.send(results);
    });
});

//show_event
app.get('/show_event', function (req, res) {
    dbConn.query('SELECT * FROM add_event where type = 1', function (error, results, fields) {
        console.log("show_event")
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

//search
app.get('/search_event/:status/:name_event', function (req, res){
    console.log("search")
    let name_event = req.params.name_event;
    let status = req.params.status;

    if (!name_event) {
        return res.status(400).send({ error: true, message: 'Please provide name_event'});
    }
    
    dbConn.query("SELECT * FROM add_event where name_event LIKE '%' ? '%' AND type = 1", [name_event,status], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/get_idEvent/:name_event/:name_producer', function (req, res){
    console.log("Get id event")
    let name_organizer = req.params.name_organizer;
    let name_event = req.params.name_event;
    let name_producer = req.params.name_producer

    if (!name_event) {
        return res.status(400).send({ error: true, message: 'Please provide name_event'});
    }
    
    dbConn.query("SELECT * FROM add_event where name_event = ? AND name_producer = ?", [name_event,name_producer], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            console.log(results[0]);
            res.end()
        }
    });
});

app.get('/check_address/:id_user', function (req, res){
    console.log("Check Address")
    let id_user = req.params.id_user;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }
    
    dbConn.query("SELECT * FROM reg_address WHERE id_user = ? ORDER BY id_address DESC", [id_user], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});

app.post('/add_distance', function (req,res){
    console.log("Add Distance")
    var data = req.body
    if (!data ) {
         return res.status(400).send({ error:true, message: 'Please Register Again' });
    }

    dbConn.query('INSERT INTO detail_distance (id_add,name_event,name_distance,distance,price,num_register) VALUES (?,?,?,?,?,?)',
     [req.body.id_add,
        req.body.name_event,
        req.body.name_distance,
        req.body.distance,
        req.body.price,
        req.body.num_register], 
        function (error, results, fields) {

        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json({"status":"ok"});
            res.end()
        }
    });
});

//insert_address
app.post('/add_address', function (req,res){
    console.log("Add Address")
    var data = req.body;
    if (!data ) {
         return res.status(400).send({ error:true, message: 'Please Register Again' });
    }

    dbConn.query('INSERT INTO reg_address SET ?', data , function (error, results, fields) {

        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json({"status":"ok"});
            res.end()
        }
    });
});

//Update transport
app.put('/update_transport/:id_user/:id_add', function (req, res) {
    console.log("update_transport")
    let id_user = req.params.id_user;
    let id_add = req.params.id_add;
    let data = req.body


        dbConn.query("UPDATE reg_address SET ? WHERE id_user = ? AND id_add = ?", [data, id_user, id_add], function (error, results, fields ) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            console.log(results)
            res.end()
        }
    });
});

//reg_event
app.post('/reg_event', function (req,res){
    console.log("User Register event")
    var data = req.body
    if (!data ) {
         return res.status(400).send({ error:true, message: 'Please Register Again' });
    }

    dbConn.query('INSERT INTO reg_event (id_user,first_name,last_name,tel,id_card,nationality,blood,distance,emergency,relation,relationTel,id_add,name_event,name_producer,pic_event) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)'
        ,[req.body.id_user
        ,req.body.first_name
        ,req.body.last_name
        ,req.body.tel
        ,req.body.id_card
        ,req.body.nationality
        ,req.body.blood
        ,req.body.distance
        ,req.body.emergency
        ,req.body.relation
        ,req.body.relationTel
        ,req.body.id_add
        ,req.body.name_event
        ,req.body.name_producer
        ,req.body.pic_event], function (error, results, fields) {

        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json({"status":"ok"});
            res.end()
        }
    });
});

//upload_payment
app.post('/upload_payment', function (req,res){
    console.log("upload_payment")
    var data = req.body;
    if (!data ) {
         return res.status(400).send({ error:true, message: 'Please Register Again' });
    }

    dbConn.query('INSERT INTO history_payment (id_user,id_add,name_event,first_name,last_name,image_link,date,time,bank) VALUES (?,?,?,?,?,?,?,?,?)', [req.body.id_user,req.body.id_add,req.body.name_event,
        req.body.first_name,req.body.last_name,req.body.image_link,req.body.date,req.body.time,req.body.bank], function (error, results, fields) {

        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json({"status":"ok"});
            res.end()
        }
    });
});

//detail_cancel
app.put('/detail_cancel/:id_history_payment', function (req, res) {
    console.log("update_transport")
    let id_history_payment = req.params.id_history_payment;
    let data = req.body


        dbConn.query("UPDATE reg_address SET ? WHERE id_history_payment = ? ", [data, id_history_payment], function (error, results, fields ) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            console.log(results)
            res.end()
        }
    });
});

app.post('/UploadStatic', function (req,res){
    console.log("UploadStatic")
    var data = req.body;
    if (!data ) {
         return res.status(400).send({ error:true, message: 'Please Register Again' });
    }

    dbConn.query('INSERT INTO history_UploadStatic (id_user,id_add,date,distance,cal,pace) VALUES (?,?,?,?,?,?)', [req.body.id_user,req.body.id_add,req.body.date,
        req.body.distance,req.body.cal,req.body.pace], function (error, results, fields) {

        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json({"status":"ok"});
            res.end()
        }
    });
});


app.get('/show_event_profile', function(req,res){
    dbConn.query('SELECT * FROM reg_event where type = 1', function (error, results, fields) {
        console.log("show_event_profile")
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/show_address/:id_user/:id_add', function(req,res){
    console.log("show_address")
    let id_user = req.params.id_user;
    let id_add = req.params.id_add;

    dbConn.query('SELECT * FROM reg_address where id_user = ? AND id_add = ?',[id_user,id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
})

//Update ProfileUser
app.put('/edit_ProfileUser/:id_user', function (req, res) {
    console.log("edit_ProfileUser")
    let id_user = req.params.id_user;
    let register = req.body

        dbConn.query("UPDATE user SET ? WHERE id_user = ?", 
        [register,id_user], function (error, results, fields ) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

//Update AddressUser
app.put('/edit_AddressUser/:id_address', function (req, res) {
    console.log("edit_AddressUser")
    let id_address = req.params.id_address;
    let data = req.body
    
        dbConn.query('UPDATE reg_address SET ? WHERE id_address = ?', 
        [data,id_address], function (error, results, fields ) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

//show_event_profile_user
app.get('/event_list/:id_user', function (req, res){
    console.log("get list event_user")
    let id_user = req.params.id_user;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }

    
    dbConn.query("SELECT * FROM reg_event Where id_user = ? AND type_submit = 1", [id_user], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

//show_event_profile_organizer
app.get('/event_organizer/:id_user', function (req, res){
    console.log("get list event_organizer")
    let id_user = req.params.id_user;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide name_organizer'});
    }

    
    dbConn.query("SELECT * FROM add_event Where id_user = ? ", [id_user], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

//show_event_payment_organizer
app.get('/event_payment_organizer/:id_user', function (req, res){
    console.log("get list event_payment_organizer")
    let id_user = req.params.id_user;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide name_organizer'});
    }

    
    dbConn.query("SELECT * FROM add_event Where id_user = ? AND type = 1", [id_user], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/event_reg_list/:id_user/:id_add', function (req, res){
    console.log("get event reg")
    let id_user = req.params.id_user;
    let id_add = req.params.id_add;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }
    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    
    dbConn.query("SELECT * FROM reg_event Where id_user = ? AND id_add = ?", [id_user,id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

//show_user_event
app.get('/show_user_event/:id_add', function (req, res){
    console.log("get event reg")
    let id_add = req.params.id_add;

    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    dbConn.query("SELECT * FROM reg_event WHERE id_add = ? AND type_submit = 0", [id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/value_distance/:id_add', function (req, res){
    console.log("get value distance")
    let id_add = req.params.id_add;

    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide first_name'});
    }
    
    dbConn.query("SELECT * FROM detail_distance where id_add = ?", [id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

//show_list_name_reg_event
app.get('/list_name_reg_event/:id_add', function (req, res){
    console.log("get list_name_reg_event")
    let id_add = req.params.id_add;

    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    
    dbConn.query("SELECT * FROM reg_event Where id_add = ? AND type_submit = 1", [id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

//show_user_send_reward
app.get('/show_user_send_reward/:id_add', function (req, res){
    console.log("get show_user_send_reward")
    let id_add = req.params.id_add;

    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    
    dbConn.query("SELECT * FROM reg_event Where id_add = ? AND status_reward = 1", [id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/user_detail_reg_event/:id_user/:id_add', function (req, res){
    console.log("get detail_reg_event")
    let id_add = req.params.id_add;
    let id_user = req.params.id_user;
    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    
    dbConn.query("SELECT * FROM reg_event Where id_user = ? AND id_add = ? AND type_submit = 1", [id_user,id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});

//approve_event
app.put('/approve_event/:id_reg_event', function (req, res) {
    console.log("submit_payment")
    let id_reg_event = req.params.id_reg_event;
    

        dbConn.query("UPDATE reg_event SET type_submit = 1 WHERE id_reg_event = ?", [id_reg_event], function (error, results, fields ) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            console.log(results);
            res.end()
        }
    });
});

app.put('/approve_history_payment/:id_history_payment', function (req, res) {
    console.log("approve_history_payment")
    let id_history_payment = req.params.id_history_payment;
    
        dbConn.query("UPDATE history_payment SET type_submit = 1 WHERE id_history_payment = ?", [id_history_payment], function (error, results, fields ) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            console.log(results);
            res.end()
        }
    });
});

//cancel_event
app.put('/cancel_event/:id_reg_event', function (req, res) {
    console.log("cancel_payment")
    let id_reg_event = req.params.id_reg_event;
    
        dbConn.query("UPDATE reg_event SET type_submit = 2 WHERE id_reg_event = ?", [id_reg_event], function (error, results, fields ) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            console.log(results);
            res.end()
        }
    });
});

app.put('/cancel_history_payment/:id_history_payment', function (req, res) {
    console.log("cancel_history_payment")
    let id_history_payment = req.params.id_history_payment;
    let data = req.body
    
        dbConn.query("UPDATE history_payment SET ? WHERE id_history_payment = ?", [data,id_history_payment], function (error, results, fields ) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            console.log(results);
            res.end()
        }
    });
});

//submit_type_distance
app.put('/submit_type_distance/:id_add/:id_user', function (req, res) {
    console.log("submit_type_distance")
    let id_add = req.params.id_add;
    let id_user = req.params.id_user;

        dbConn.query("UPDATE reg_event SET status_reward = 1 WHERE id_add = ? AND id_user = ?", [id_add,id_user], function (error, results, fields ) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            console.log(results);
            res.end()
        }
    });
});

//submit_type_send
app.put('/submit_type_send/:id_add/:id_user', function (req, res) {
    console.log("submit_type_send")
    let id_add = req.params.id_add;
    let id_user = req.params.id_user;

        dbConn.query("UPDATE reg_event SET status_send = 1 WHERE id_add = ? AND id_user = ?", [id_add,id_user], function (error, results, fields ) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            console.log(results);
            res.end()
        }
    });
});

app.get('/show_user_payment/:id_history_payment', function (req, res){
    console.log("show user payment id_his")
    let id_history_payment = req.params.id_history_payment;

    if (!id_history_payment) {
        return res.status(400).send({ error: true, message: 'Please provide id_history_payment'});
    }
    
    dbConn.query("SELECT * FROM history_payment WHERE  id_history_payment = ?", [id_history_payment], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});

app.get('/show_history_user_payment/:id_history_payment', function (req, res){
    console.log("show user payment")
    let id_history_payment = req.params.id_history_payment;

    if (!id_history_payment) {
        return res.status(400).send({ error: true, message: 'Please provide id_history_payment'});
    }
    
    dbConn.query("SELECT * FROM history_payment WHERE id_history_payment = ?", [id_history_payment], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});


app.get('/setName/:id_user', function (req, res){
    console.log("set Name")
    let id_user = req.params.id_user;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }

    
    dbConn.query("SELECT * FROM user Where id_user = ?", [id_user], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});

app.get('/getId_reg_event/:id_user/:id_add', function (req, res){
    console.log("show user payment")
    let id_user = req.params.id_user;
    let id_add = req.params.id_add;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }
    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    
    dbConn.query("SELECT * FROM reg_event Where id_user = ? AND id_add = ?", [id_user,id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});

//show_profile_payment
app.get('/show_status_payment_user/:id_user', function (req, res){
    console.log("show status payment user")
    let id_user = req.params.id_user;


    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }

    dbConn.query("SELECT * FROM reg_event Where id_user = ?", [id_user], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/show_stat_distance/:id_user/:id_add', function (req, res){
    console.log("show_stat_distance")
    let id_user = req.params.id_user;
    let id_add = req.params.id_add;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }
    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    dbConn.query("SELECT * FROM history_uploadstatic Where id_user = ? AND id_add = ?", [id_user,id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/check_user_reg_event/:id_user/:id_add', function (req, res){
    console.log("check user reg event")
    let id_user = req.params.id_user;
    let id_add = req.params.id_add;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }

    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    dbConn.query("SELECT * FROM reg_event WHERE id_user = ? AND id_add = ? AND type_submit = 1", [id_user,id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});

app.get('/check_organizer_event/:id_add', function (req, res){
    console.log("check organizer event")
    let id_add = req.params.id_add;

    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    dbConn.query("SELECT * FROM add_event WHERE id_add = ?", [id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});

//showpayment_event
app.get('/show_payment/:id_add', function (req, res){
    console.log("show_payment")
    let id_add = req.params.id_add;

    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }


    dbConn.query("SELECT * FROM add_event WHERE id_add = ?", [id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});


app.get('/list_payment_event/:id_user', function (req, res){
    console.log("check user reg event")
    let id_user = req.params.id_user;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }

    dbConn.query("SELECT * FROM reg_event WHERE id_user = ? ", [id_user], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/list_user_reg_payment/:id_add', function (req, res){
    console.log("List user reg event")
    let id_add = req.params.id_add;
    
    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    dbConn.query("SELECT * FROM history_payment WHERE id_add = ? AND type_submit = 0" , [id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});


app.get('/check_send_submit/:id_add', function (req, res){
    console.log("List user reg event")
    let id_add = req.params.id_add;

    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    dbConn.query("SELECT * FROM reg_event WHERE id_add = ?", [id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/check_user_payment_event/:id_user', function (req, res){
    console.log("Check user payment event")
    let id_user = req.params.id_user;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    dbConn.query("SELECT * FROM history_payment WHERE id_user = ?", [id_user], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/profile_user/:id_user', function (req, res){
    console.log("Show Profile")
    let id_user = req.params.id_user;

    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }

    dbConn.query("SELECT * FROM user WHERE id_user = ?", [id_user], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});


app.get('/list_history_distance/:id_user/:id_add', function (req, res){
    console.log("list_history_distance")
    let id_user = req.params.id_user;
    let id_add = req.params.id_add;
    if (!id_user) {
        return res.status(400).send({ error: true, message: 'Please provide id_user'});
    }
    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    dbConn.query("SELECT * FROM history_uploadstatic WHERE id_user = ? AND id_add = ?", [id_user,id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

app.get('/check_status_payment/:id_reg_event', function (req, res){
    console.log("check_status_payment")
    let id_reg_event = req.params.id_reg_event;

    if (!id_reg_event) {
        return res.status(400).send({ error: true, message: 'Please provide id_reg_event'});
    }

    dbConn.query("SELECT * FROM reg_event WHERE id_reg_event = ?", [id_reg_event], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});

app.get('/check_status_event/:id_add', function (req, res){
    console.log("check_status_event")
    let id_add = req.params.id_add;

    if (!id_add) {
        return res.status(400).send({ error: true, message: 'Please provide id_add'});
    }

    dbConn.query("SELECT * FROM add_event WHERE id_add = ?", [id_add], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results[0]);
            res.end()
        }
    });
});