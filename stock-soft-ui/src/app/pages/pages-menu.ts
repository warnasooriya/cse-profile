import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Dashboard',
    icon: 'shopping-cart-outline',
    link: '/pages/dashboard',
    home: true,
  },
  {
    title: 'Equity Holding',
    icon: 'shuffle-2-outline',
    link: '/pages/trade',
    home: true,
  },
  
  {
    title: 'Buy Details',
    icon: 'shopping-cart',
    link: '/pages/buy',
    home: true,
  },
  {
    title: 'Sell Details',
    icon: 'trending-up-outline',
    link: '/pages/sell',
    home: true,
  },
  {
    title: 'Dividend',
    icon: 'layout-outline',
    link: '/pages/divident',
    home: true,
  },
  {
    title: 'Deposit',
    icon: 'keypad-outline',
    link: '/pages/deposit',
    home: true,
  },
  {
    title: 'Transactions',
    icon: 'options-outline',
    link: '/pages/transactions',
    home: true,
  },
  {
    title: 'Profit Details',
    icon: 'pie-chart-outline',
    link: '/pages/profit',
    home: true,
  },
  
];
