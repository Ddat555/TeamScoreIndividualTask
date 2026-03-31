export interface SummaryStatistic {
  totalSum: number;
  countCosts: number;
}

export interface AbstractStatistic {
  name: string;
  totalSum: number;
}

export interface CategoryStatistic extends AbstractStatistic {
  percentage: number;
  color: string;
}

export interface TypePaymentStatistic extends AbstractStatistic {
  percentage: number;
}

export interface SellerStatistic extends AbstractStatistic {}

export interface DetailedStatisticInfo<T extends AbstractStatistic> {
  summaryStatistic: SummaryStatistic;
  statisticList: T[];
}

export type CategoryStatisticInfo = DetailedStatisticInfo<CategoryStatistic>;
export type PaymentStatisticInfo = DetailedStatisticInfo<TypePaymentStatistic>;
export type SellerStatisticInfo = DetailedStatisticInfo<SellerStatistic>;
