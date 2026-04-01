const amqp = require('amqplib');

const QUEUE = 'commandes';
const STARTUP_RETRY_DELAY_MS = 5000;

function wait(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}

async function demarrerConsommateur() {
    for (;;) {
        try {
            const conn = await amqp.connect('amqp://admin:admin@rabbitmq');
            const channel = await conn.createChannel();

            await channel.assertQueue(QUEUE, { durable: true });
            console.log('👂 Consommateur en attente de messages...');

            conn.on('close', () => {
                console.error('Connexion RabbitMQ fermée pour le consommateur');
                setTimeout(demarrerConsommateur, STARTUP_RETRY_DELAY_MS);
            });

            conn.on('error', (error) => {
                console.error('RabbitMQ indisponible pour le consommateur:', error.message);
            });

            channel.consume(QUEUE, (msg) => {
                if (msg) {
                    const commande = JSON.parse(msg.content.toString());

                    console.log('');
                    console.log('🔔 NOUVELLE COMMANDE REÇUE !');
                    console.log(' Client :', commande.client);
                    console.log(' Produit :', commande.produit);
                    console.log(' ID :', commande.id);
                    console.log('📧 Email de confirmation envoyé (simulé)');
                    console.log('');

                    channel.ack(msg);
                }
            });

            return;
        } catch (error) {
            console.error('Echec du démarrage du consommateur:', error.message);
            await wait(STARTUP_RETRY_DELAY_MS);
        }
    }
}

demarrerConsommateur();
