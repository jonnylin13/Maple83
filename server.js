'use strict';

const express = require('express');
const app = express();

const cors = require('cors');
const bodyParser = require('body-parser');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors());

// == PUBLIC DATA ==
// GET
app.get('/api/news/get', (req, res) => {

    let news = [
        {
            author: 'Jono',
            type: 'Announcement',
            dateCreated: '11/5/2017',
            body: 'This is a test post'
        },
        {
            author: 'Jono',
            type: 'Announcement',
            dateCreated: '11/5/2017',
            body: 'This is a test post'
        },
        {
            author: 'Jono',
            type: 'Announcement',
            dateCreated: '11/5/2017',
            body: 'This is a test post'
        }
    ];
    res.json(news);

});

/// POST (AUTHENTICATED)


// Listen on port 3001
app.listen(3001);
console.log('Maple83Site serving data on localhost:3001');