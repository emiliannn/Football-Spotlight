import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Player } from '../../../interfaces/player';
import { PlayerService } from '../../services/player.service';
import { PlayerInfo } from '../../../interfaces/playersInfo';
@Component({
  selector: 'app-player-details',
  templateUrl: './player-details.component.html',
  styleUrl: './player-details.component.css'
})
export class PlayerDetailsComponent implements OnInit {
  player: PlayerInfo;
  playerToBeUpdated: Player = {} as Player;
  currentMode: string;
  @Output() editEventEmitter = new EventEmitter<void>();
  isEditMode: Boolean = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private playerService: PlayerService) {
    this.player = data.item;
    this.currentMode = data.mode;
  }
  ngOnInit(): void {
   
  }
  onEditClick(mode: Boolean) {
    // this.editEventEmitter.emit();
    this.isEditMode = mode;
    if(!mode) {
      this.playerToBeUpdated.citizenship = this.player.citizenship;
      this.playerToBeUpdated.age = this.player.age;
      this.playerToBeUpdated.contractID = this.player.contractID;
      this.playerToBeUpdated.dateOfBirth = this.player.dateOfBirth;
      this.playerToBeUpdated.foot = this.player.foot;
      this.playerToBeUpdated.fullName = this.player.fullName;
      this.playerToBeUpdated.height = this.player.height;
      this.playerToBeUpdated.id = this.player.id;
      this.playerToBeUpdated.outfitter = this.player.outfitter;
      this.playerToBeUpdated.position = this.player.position;

      this.playerService.updatePlayer(this.player).subscribe((response) => {
        console.log("response", response);
      }, (error) => {
        console.log("error", error);
      });
    }
  }


  deleteItem(id: string) {
    this.playerService.deletePlayer(id).subscribe((response) => {
      console.log("response", response);
    }, (error) => {
      console.log("error", error);
    });
  }
  
}
