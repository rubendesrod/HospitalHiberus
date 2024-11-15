"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const eureka_js_client_1 = require("eureka-js-client");
// Configura el cliente de Eureka
const eurekaClient = new eureka_js_client_1.Eureka({
    instance: {
        app: 'MEDICOS',
        instanceId: 'medicos',
        hostName: 'localhost',
        ipAddr: '127.0.0.1',
        statusPageUrl: 'http://localhost:8082/info', // Puede ser cualquier endpoint informativo
        port: {
            '$': 8082,
            '@enabled': true,
        },
        vipAddress: 'medicos',
        dataCenterInfo: {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
        },
    },
    eureka: {
        host: 'localhost',
        port: 8761,
        servicePath: '/eureka/apps/',
    },
});
// Inicia el cliente de Eureka
eurekaClient.start((error) => {
    if (error) {
        console.error('Error al registrar medicos en Eureka:', error);
    }
    else {
        console.log('Correcto resgitro de medicos en Eureka');
    }
});
exports.default = eurekaClient;