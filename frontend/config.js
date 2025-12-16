const CONFIG = {
    GATEWAY_BASE_URL: "http://localhost:9000",

    SERVICES: {
        student: {
            serviceName: "/student-service",
            serviceEndpoint: "/students",
            fallbackUrl: "http://localhost:9001",
        },
        company: {
            serviceName: "/company-service",
            serviceEndpoint: "/companies",
            fallbackUrl: "http://localhost:9002",
        },
        placement: {
            serviceName: "/placement-service",
            serviceEndpoint: "/placements",
            fallbackUrl: "http://localhost:9003",
        },
    },
};
