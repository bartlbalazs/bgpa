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

  @Input()
  xLabel: string

  @Input()
  yLabel: string

  @Input()
  seriesLabel: string

  @Input()
  rLabel: string

  constructor() { }

}
