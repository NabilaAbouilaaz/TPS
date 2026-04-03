const amqp = require('amqplib');

let connection;
let channel;
const QUEUE = 'commandes';

async function connectRabbitMQ() {
    if (channel) {
        return channel;
    }

    connection = await amqp.connect('amqp://admin:admin@rabbitmq');
    channel = await connection.createChannel();
    await channel.assertQueue(QUEUE, { durable: true });
    console.log('✅ RabbitMQ connecté');

    connection.on('close', () => {
        console.error('Connexion RabbitMQ fermée');
        connection = undefined;
        channel = undefined;
    });

    connection.on('error', (error) => {
        console.error('RabbitMQ indisponible:', error.message);
    });

    return channel;
}

function publierCommande(commande) {
    if (!channel) {
        throw new Error('RabbitMQ non connecté');
    }

    const msg = JSON.stringify(commande);
    channel.sendToQueue(QUEUE, Buffer.from(msg), { persistent: true });
    console.log('📤 Message publié dans RabbitMQ');
}

module.exports = { connectRabbitMQ, publierCommande };
