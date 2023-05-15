package com.team.ShopSystem.sys.service.impl;

import com.team.ShopSystem.config.ConstantsProperties;
import com.team.ShopSystem.sys.entity.*;
import com.team.ShopSystem.sys.mapper.*;
import com.team.ShopSystem.sys.service.IEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xby
 * @since 2023-05-09
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements IEventService {
    @Autowired
    EventMapper eventMapper;
    @Autowired
    ConstantsProperties constants;
    @Autowired
    EventFundsMapper eventFundsMapper;
    @Autowired
    TransferRecordsMapper transferRecordsMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    EventApplyMapper eventApplyMapper;
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateEvents(){
        List<Event> eventList = eventMapper.show();
        for (Event event : eventList) {
            if (!event.getStartDate().isBefore(LocalDate.now())) {
                event.setStatus(constants.getApproved());
            }
            else if(event.getEndDate().isBefore(LocalDate.now())){
                event.setStatus(constants.getRejected());
                Float left_funds = eventFundsMapper.selectFundsByEventId(event.getId());
                if(left_funds > 0){
                    transferRecordsMapper.insert(new TransferRecords("admin_intermediate",left_funds,"admin_profit",LocalDate.now(),"remaining funds refunded"));
                    Admin admin = adminMapper.get();
                    admin.setIntermediateAccount(admin.getIntermediateAccount() - left_funds);
                    admin.setProfitAccount(admin.getProfitAccount() + left_funds);
                    adminMapper.updateById(admin);
                    EventFunds eventFunds = eventFundsMapper.selectByEventId(event.getId());
                    eventFunds.setFunds(0.0f);
                    eventFundsMapper.updateById(eventFunds);
                }
                List<EventApply> eventApplies = eventApplyMapper.selectByEventId(event.getId());
                for (EventApply eventApply : eventApplies) {
                    eventApply.setStatus(constants.getRejected());
                    eventApplyMapper.updateById(eventApply);
                }
            }
            eventMapper.updateById(event);
        }
    }
}
