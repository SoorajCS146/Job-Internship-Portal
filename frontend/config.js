const CONFIG = {
  GATEWAY_BASE_URL: "http://localhost:9000",

  SERVICES: {
    student: {
      path: "/students",
      fallbackUrl: "http://localhost:9001"
    },
    company: {
      path: "/companies",
      fallbackUrl: "http://localhost:9002"
    },
    placement: {
      path: "/placements",
      fallbackUrl: "http://localhost:9003"
    }
  }
};