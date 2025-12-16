async function apiFetch(serviceName, endpoint = "", options = {}) {
    const service = CONFIG.SERVICES[serviceName];
    if (!service) {
        throw new Error(`Service "${serviceName}" is not configured.`);
    }

    const gatewayUrl = `${CONFIG.GATEWAY_BASE_URL}${service.path}${endpoint}`;
    console.log("Gateway URL:", gatewayUrl)

    try {
        const response = await fetch(gatewayUrl, options);
        if (!response.ok) {
            throw new Error(`Gateway request failed with status ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.warn(`Gateway request to ${gatewayUrl} failed. Trying fallback...`, error);
        
        const fallbackUrl = `${service.fallbackUrl}${service.path}${endpoint}`;
        
        try {
            const fallbackResponse = await fetch(fallbackUrl, options);
            if (!fallbackResponse.ok) {
                throw new Error(`Fallback request failed with status ${fallbackResponse.status}`);
            }
            return await fallbackResponse.json();
        } catch (fallbackError) {
            console.error(`Fallback request to ${fallbackUrl} also failed.`, fallbackError);
            throw new Error(`Both gateway and fallback requests failed for service "${serviceName}".`);
        }
    }
}