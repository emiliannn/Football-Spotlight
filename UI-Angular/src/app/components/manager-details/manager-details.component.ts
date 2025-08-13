import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Manager } from '../../../interfaces/manager';
import { ManagerService } from '../../services/manager.service';

@Component({
  selector: 'app-manager-details',
  templateUrl: './manager-details.component.html',
  styleUrl: './manager-details.component.css'
})
export class ManagerDetailsComponent  implements OnInit {
  manager: Manager;
  currentMode: string;
  @Output() editEventEmitter = new EventEmitter<void>();
  isEditMode: Boolean = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private managerService: ManagerService) {
    this.manager = data.item;
    this.currentMode = data.mode;
  }
  ngOnInit(): void {
    // throw new Error('Method not implemented.');
  }

  
  onEditClick(mode: Boolean) {
    // this.editEventEmitter.emit();
    this.isEditMode = mode;
    if(!mode) {
      this.managerService.updateManager(this.manager).subscribe((response) => {
        console.log("response", response);
      }, (error) => {
        console.log("error", error);
      });
    }
  }
  updateItem(manager: Manager) {
    //
  }

  deleteItem(id: string) {
    this.managerService.deleteManager(id).subscribe((response) => {
      console.log("response", response);
    }, (error) => {
      console.log("error", error);
    });
  }



  
}
