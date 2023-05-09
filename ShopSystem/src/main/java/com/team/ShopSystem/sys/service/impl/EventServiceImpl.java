package com.team.ShopSystem.sys.service.impl;

import com.team.ShopSystem.sys.entity.Event;
import com.team.ShopSystem.sys.mapper.EventMapper;
import com.team.ShopSystem.sys.service.IEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
