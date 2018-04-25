var express = require('express');
var router = express.Router();
var domain = require('../domain/domain');

/* GET users listing. */
router.get('/', async function (req, res) {
    try {
        return res.json(await domain.getHosts());
    } catch (err) {
        return res.json(err);
    }
});

router.get('/:id', async function (req, res) {
    try {
        return res.json(await domain.findHost(req.params.id));
    } catch (err) {
        return res.json(err);
    }
})

router.put('/:id', async function (req, res) {
    try {
         let host = await domain.findHost(req.params.id);
         host.command = req.body.command;        
        return res.json(await domain.updateHostCommand(host));
    } catch (err) {
        return res.json(err);
    }
})

module.exports = router;