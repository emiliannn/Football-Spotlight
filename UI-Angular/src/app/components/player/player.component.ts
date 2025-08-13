import { Player } from '../../../interfaces/player';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PlayerDetailsComponent } from '../player-details/player-details.component';
import { PlayerService } from '../../services/player.service';
import { PlayerInfo } from '../../../interfaces/playersInfo';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrl: './player.component.css'
})
export class PlayerComponent {
 @Input() player: PlayerInfo | undefined;
 @Input() currentMode!: string;
//  @Output() taskEventEmitter = new EventEmitter();

 constructor(private dialog: MatDialog) { }

 ngOnInit(): void {
  // console.log("this player- ",this.player);
 }

 openPlayerDetailsPopup() {
  this.dialog.open(PlayerDetailsComponent, {
    width: '45em',
    height: '35em',
    data: { item: this.player, mode: this.currentMode }});
}
}