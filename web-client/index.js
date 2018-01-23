/**
 * Small reporting web application
 */
const url = require('url');

const config = require('./app.json');

const express = require('express');
const pug = require('pug');

const Client = new require('node-rest-client').Client;

const client = new Client();

const app = express();

const serverURL = url.format({
    protocol : "http",
    hostname : config.serverHost,
    port : config.serverPort
});

console.log(`Constructed server url : ${serverURL}`);

app.set('view engine', 'pug');

/**
 * One shot fetch and render
 */
app.get('/report.html', (req, res) => {
    let shiftUrl = [serverURL, config.employeesEndpoint, config.shiftEndpoint].join('/');
    console.log(`Getting ${shiftUrl}`);
    let employeeShifts = client.get(shiftUrl, items => {
       console.log(`received: ${JSON.stringify(items)}`);
       let data = {
          title : "Shift report",
           items: items,
           formatDate: formatDate
       };
       res.render('report', data);
    });
});

app.listen(config.port, () => console.log(`Listening on port ${config.port}`));


function formatDate(date) {
    return new Date(date).toISOString()
        .replace(/T/, ' ')
        .replace(/\..+/, '')
}
