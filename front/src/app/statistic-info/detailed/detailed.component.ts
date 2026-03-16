import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatisticNodeComponent } from './statistic-node/statistic-node.component';
import { AbstractStatistic } from '../../models/statistic.models';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-detailed',
  standalone: true,
  imports: [CommonModule, StatisticNodeComponent, FormsModule, StatisticNodeComponent],
  templateUrl: './detailed.component.html',
  styleUrl: './detailed.component.css'
})
export class DetailedComponent {
  @Input() title: string = '';
  @Input() data: AbstractStatistic[] = [];
  @Input() showPercentage: boolean = false;
  @Input() showColor: boolean = false;
}