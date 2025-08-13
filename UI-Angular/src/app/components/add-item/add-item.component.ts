import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Player } from '../../../interfaces/player';
import { Manager } from '../../../interfaces/manager';
import { PlayerService } from '../../services/player.service';
import { FootballClub } from '../../../interfaces/footballClub';
import { Contract } from '../../../interfaces/contract';
import { CtService } from '../../services/ct.service';
import { ManagerService } from '../../services/manager.service';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrl: './add-item.component.css'
})
export class AddItemComponent implements OnInit{
  item: string;
  managers: Manager[] = [];

  footballClubs: FootballClub [] = [{
    id: "1",
    name: "Example Club",
    squadSize: "20",
    averageAge: 25,
    stadium: "Example Stadium",
    managerID: "12345"
  }];

  response: any;

  player: Player = {
    id: "1",
    fullName: "Player X",
    contractID: "2",
    dateOfBirth: "1990-09-20",
    age: 22,
    height: 1.76,
    citizenship: "-",
    position: "Attack - Second Striker",
    foot: "right",
    outfitter: "Puma",
  };
  manager: Manager = {
    id: "1",
    fullName: "Manager X",
    contractID: "1",
    dateOfBirth: "1990-09-20",
    age: 22,
    nationality: "-",
    role: "Coach",
  };


  footballClub: FootballClub =  {
    id: "1",
    name: "Example Club",
    squadSize: "20",
    averageAge: 25,
    stadium: "Example Stadium",
    managerID: "12345"
  };

  contract: Contract = {
    id: "1",
    contractExpires:"2021-09-20",
    contractOption:"1",
    currentClubID:"1",
    joined: "2020-09-20",
    previousClubID: "2"
  };

  ngOnInit() {

    this.fetchManagers();
    // this.fetchFootballClubs();
  }

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,private managerService:ManagerService, private playerService: PlayerService, private ctService: CtService) {
    this.item = data;
  }


  fetchManagers() {
    this.managerService.getManagers()
      .subscribe(
        data => {
          this.response = data.managers;
          if (this.response !== 'error') {
            if (this.response !== undefined && this.response.length > 0) {
              this.managers = this.response;
            } else {
              this.managers = [];
            }
          }
          else {
            alert(this.response.message);
          }
        }
      )
  }


  fetchFootballClubs() {
    this.ctService.getFootballClubs()
      .subscribe(
        data => {
          this.response = data.footballClubs;
          if (this.response !== 'error') {
            if (this.response !== undefined && this.response.length > 0) {
              this.footballClubs = this.response;
            } else {
              this.footballClubs = [];
            }
            console.log("football teams-> ",this.footballClubs);
          }
          else {
            alert(this.response.message);
          }
        }
      )
  }

  addPlayer(id: string) {
    this.player.contractID = id;

    this.playerService.addPlayer(this.player).subscribe(response=>{
      console.log(response.data);
    });
  }

   addManager(id: string) {
    this.manager.contractID = id;
    this.managerService.addManager(this.manager).subscribe(response=>{
      console.log(response.data);
    });
  }

  addManager_and_Contract() {
    const id = uuidv4();
    this.addManager(id);
    this.addContract(id)

  }

  addPlayer_and_Contract() {
    const id = uuidv4();
    this.addPlayer(id);
    this.addContract(id)

  }
  addFootballClub() {
    this.ctService.addFootballClub(this.footballClub).subscribe(response=>{
      console.log(response.data);
    });
  }

  addContract(id: string) {
    this.contract.id = id;
    this.ctService.addContract(this.contract).subscribe(response=>{
      console.log(response.data);
    });
  }


}
