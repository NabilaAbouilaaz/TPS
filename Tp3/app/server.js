const redis = require('redis');

const redisClient = redis.createClient({
    url: 'redis://redis:6379'
});

redisClient.connect().catch(console.error);



const express = require('express');

const { connectMongo } = require('./services/mongodb');
const { connectRabbitMQ } = require('./services/rabbitmq');
const redis = require('./services/redis');
const commandesRouter = require('./routes/commandes');

const app = express();
const PORT = Number(process.env.PORT) || 3000;
const INSTANCE = process.env.INSTANCE || 'Instance-?';
const STARTUP_RETRY_DELAY_MS = 5000;

app.use(express.json());

app.get('/health', async (req, res) => {
    try {
        await redis.ping();

        res.json({
            status: 'ok',
            instance: INSTANCE
        });
    } catch (error) {
        res.status(503).json({
            status: 'error',
            instance: INSTANCE,
            erreur: error.message
        });
    }
});

app.use('/commandes', commandesRouter);

app.use((req, res) => {
    res.status(404).json({ erreur: 'Route introuvable' });
});

async function start() {
    for (;;) {
        try {
            await connectMongo();
            await redis.ping();
            await connectRabbitMQ();

            app.listen(PORT, '0.0.0.0', () => {
                console.log(`Serveur ${INSTANCE} demarre sur le port ${PORT}`);
            });

            return;
        } catch (error) {
            console.error('Echec du demarrage du serveur:', error.message);
            await new Promise((resolve) => setTimeout(resolve, STARTUP_RETRY_DELAY_MS));
        }
    }
}

start();
