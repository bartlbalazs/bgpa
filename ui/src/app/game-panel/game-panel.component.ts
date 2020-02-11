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
  game: { x: number, y: number, r: number, series: string, thumbnail: string, name: string, id: number }

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
