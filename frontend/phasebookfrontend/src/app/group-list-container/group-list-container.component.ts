import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-group-list-container',
  templateUrl: './group-list-container.component.html',
  styleUrls: ['./group-list-container.component.css', './group-list-container.form.component .css']
})
export class GroupListContainerComponent implements OnInit {

  showEditForm: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

}
