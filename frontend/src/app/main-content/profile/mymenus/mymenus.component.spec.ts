import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MymenusComponent } from './mymenus.component';

describe('MymenusComponent', () => {
  let component: MymenusComponent;
  let fixture: ComponentFixture<MymenusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MymenusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MymenusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
