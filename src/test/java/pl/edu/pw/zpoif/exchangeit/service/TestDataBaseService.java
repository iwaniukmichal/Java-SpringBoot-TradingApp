package pl.edu.pw.zpoif.exchangeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoif.exchangeit.repository.ControllerTestsRepository;

@Service
public class TestDataBaseService {
    private final ControllerTestsRepository controllerTestsRepository;

    @Autowired
    public TestDataBaseService(ControllerTestsRepository controllerTestsRepository) {
        this.controllerTestsRepository = controllerTestsRepository;
    }

    public ControllerTestsRepository getControllerTestsRepository() {
        return controllerTestsRepository;
    }
}
