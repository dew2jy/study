package com.example.demo.events;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//자동 unwrapped...
public class EventResource extends EntityModel<Event> {
   public EventResource(Event event, Link... links) {
       super(event, links);
       add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
   }
}
