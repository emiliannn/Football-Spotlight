import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ManagerDetailsComponent } from '../manager-details/manager-details.component';
import { MatDialog } from '@angular/material/dialog';
import { Manager } from '../../../interfaces/manager';

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrl: './manager.component.css'
})
export class ManagerComponent {
  @Input() manager: Manager | undefined;
  @Input() currentMode!: string;
  @Output() taskEventEmitter = new EventEmitter();
 
  constructor(private dialog: MatDialog) { }
 
  ngOnInit(): void {
  }
 


 openManagerDetailsPopup() {
  this.dialog.open(ManagerDetailsComponent, {
    width: '45em',
    height: '35em',
    data: { item: this.manager, mode: this.currentMode }});
 }
}

