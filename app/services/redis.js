const Redis = require('ioredis');

const redis = new Redis({
    host: 'redis',
    port: 6379
});

redis.on('connect', () => {
    console.log('✅ Redis connecté');
});

redis.on('error', (error) => {
    console.error('Redis indisponible:', error.message);
});

module.exports = redis;
