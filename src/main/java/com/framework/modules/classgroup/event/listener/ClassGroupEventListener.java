package com.framework.modules.classgroup.event.listener;

import com.framework.modules.classgroup.event.ClassGroupDeactivatedEvent;
import com.framework.modules.classgroup.service.ClassGroupUserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ClassGroupEventListener {

    private final ClassGroupUserService classGroupUserService;

    public ClassGroupEventListener(ClassGroupUserService classGroupUserService) {
        this.classGroupUserService = classGroupUserService;
    }

    @EventListener
    public void handleClassGroupDeactivated(ClassGroupDeactivatedEvent event) {
        if (event == null || event.getClassGroup() == null) {
            return;
        }
        classGroupUserService.removeSubscriptionByClassGroup(event.getClassGroup());
    }
}
