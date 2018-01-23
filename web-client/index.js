/**
 * Small reporting web application that renders templates for employee and
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

app.get('/report.html', (req, res) => {
    let shiftUrl = [serverURL, config.employeesEndpoint, config.shiftEndpoint].join('/');
    console.log(`Getting ${shiftUrl}`);
    client.get(shiftUrl, items => {
       console.log(`received: ${JSON.stringify(items)}`);

        res.render('report', {
            title: "Shift report",
           items: items,
           formatDate: formatDate
        });
    });
});

app.get('/employee.html', (req, res) => {
    let employeeUrl = [serverURL, config.employeesEndpoint].join('/');
    console.log(`Getting ${employeeUrl}`);
    client.get(employeeUrl, employee => {
        console.log(`received ${JSON.stringify(employee)}`);

        res.render('employee', {
            item: employee
        });
    });
});

app.listen(config.port, () => console.log(`Listening on port ${config.port}`));


function formatDate(date) {
    return new Date(date).toISOString()
        .replace(/T/, ' ')
        .replace(/\..+/, '')
}
