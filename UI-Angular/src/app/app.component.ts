import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Player } from '../interfaces/player';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { Manager } from '../interfaces/manager';
import { MatDialog } from '@angular/material/dialog';
import { AddItemComponent } from './components/add-item/add-item.component';
import { PlayerService } from './services/player.service';
import { ManagerService } from './services/manager.service';
import { CtService } from './services/ct.service';
import { User } from '../interfaces/user';
import { PlayerInfo } from '../interfaces/playersInfo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit , AfterViewInit{
  title = 'UI-Angular';

  response: any;
  playersList: PlayerInfo [] = [];
  managersList: Manager [] = [];
  

  user: User = {
    id: '',
    fullName: '',
    mode: ''
  };
  // currentMode!: string;


  constructor(private dialog: MatDialog, private iconRegistry: MatIconRegistry, private sanitizer: DomSanitizer, private playerService: PlayerService, private ct: CtService , private managerService: ManagerService) {


  }



  ngAfterViewInit(): void {
  }
  
   ngOnInit() {
    this.fetchPlayers();
    this.fetchManagers();
    this.fetchUser("2");
  }


  openAddItemPopup(item: string){
    this.dialog.open(AddItemComponent, {
      width: '45em',
      height: '35em',
      data: item
  })}

  fetchUser(id: string) {
    this.ct.getUser(id)
     .subscribe(
       data => {
           if (data !== undefined) 
             this.user = data;
      }
     )
 } 

   fetchPlayers() {
     this.playerService.getPlayersInfo()
      .subscribe(
        data => {
          this.response = data.players;
          if (this.response !== 'error') {
            if (this.response !== undefined && this.response.length > 0) {
              this.playersList = this.response;
            } else {
              this.playersList = [];
            }
          }
          else {
            alert(this.response.message);
          }
        }
      )
      console.log("playersList",this.playersList)
  }   

  fetchManagers() {
    this.managerService.getManagers()
      .subscribe(
        data => {
          this.response = data.managers;
          if (this.response !== 'error') {
            if (this.response !== undefined && this.response.length > 0) {
              this.managersList = this.response;
            } else {
              this.managersList = [];
            }
          }
          else {
            alert(this.response.message);
          }
        }
      )
  }   

}
