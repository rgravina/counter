package counter.broker;

import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CounterInstanceBindingService implements ServiceInstanceBindingService {
    private Map<String, ServiceInstanceBinding> instances = new HashMap<>();

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {
        String bindingId = request.getBindingId();
        String serviceInstanceId = request.getServiceInstanceId();

        ServiceInstanceBinding binding = instances.get(bindingId);
        if (binding != null) {
            throw new ServiceInstanceBindingExistsException(serviceInstanceId, bindingId);
        }

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("url", System.getenv("COUNTER_URL"));
        credentials.put("key", bindingId + serviceInstanceId);

        new ServiceInstanceBinding(bindingId, serviceInstanceId, credentials, null, request.getBoundAppGuid());

        return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);
    }

    @Override
    public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
        String bindingId = request.getBindingId();
        ServiceInstanceBinding binding = instances.get(bindingId);

        if (binding == null) {
            throw new ServiceInstanceBindingDoesNotExistException(bindingId);
        }

        instances.remove(bindingId);
    }
}
