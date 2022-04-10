const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const app = express();

const stripe = require('stripe')('sk_test_51HICEULBnGF2noZqWp24T23RbBr7BJTWEd8NcK4BYOGzPvvkD9jzh9CJgKx2U4XQ6MEf0hwOwcfByMrQdNyPLGV000cuYl2nsO');


app.use(express.json());
app.use(express.urlencoded);
app.use(cors());


app.get('/',(req,res) => {
    res.json({
        message: "Server is working"
    })
})
app.post('/payment-sheet', async (req, res) => {

    try {
        const price = req.body.price;
        const email = req.body.email;


        const customer = await stripe.customers.create();
        const ephemeralKey = await stripe.ephemeralKeys.create(
            {customer: customer.id},
            {apiVersion: '2020-08-27'}
        );
        const paymentIntent = await stripe.paymentIntents.create({
            amount: price * 100,
            currency: 'usd',
            customer: customer.id,
            receipt_email: email,
            automatic_payment_methods: {
                enabled: true,
            },
        });

        res.json({
            paymentIntent: paymentIntent.client_secret,
            ephemeralKey: ephemeralKey.secret,
            customer: customer.id,
            publishableKey: 'pk_test_51HICEULBnGF2noZqiD4dgSHxm9gBJkGLvQXuqVfn9roV4qOSZaX1Ksudl7nQaQuEQf3FtbXQ4h2j0kF3Xdap9dfi00saU5TkbN'
        });
    } catch (e) {
        res.json(e);
    }
});

app.post('/create-payment-intent', async (req, res) => {

    console.log('Entered here in payment');

    console.log(req.body);
    const price = req.body.price;
    const email = req.body.email;

    const paymentIntent = await stripe.paymentIntents.create({
        amount: price * 100,
        currency: 'usd',
        receipt_email: email,
        // Verify your integration in this guide by including this parameter
        metadata: {
            integration_check: 'accept_a_payment'
        },
    });

    res.json({
        client_secret: paymentIntent.client_secret
    });
})

module.exports = app;