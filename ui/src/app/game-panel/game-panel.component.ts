import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-game-panel',
  templateUrl: './game-panel.component.html',
  styleUrls: ['./game-panel.component.css']
})
export class GamePanelComponent {

  @Input()
  maxHeight: number;

  @Input()
  game: any[]

  constructor() { }

}
