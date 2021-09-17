import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserChatContainerComponent } from './user-chat-container.component';

describe('UserChatContainerComponent', () => {
  let component: UserChatContainerComponent;
  let fixture: ComponentFixture<UserChatContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserChatContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserChatContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
