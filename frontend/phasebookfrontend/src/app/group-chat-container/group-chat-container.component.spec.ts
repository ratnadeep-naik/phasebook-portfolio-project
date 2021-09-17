import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupChatContainerComponent } from './group-chat-container.component';

describe('GroupChatContainerComponent', () => {
  let component: GroupChatContainerComponent;
  let fixture: ComponentFixture<GroupChatContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupChatContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupChatContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
