import React from 'react';
import styled, { css } from 'styled-components';
import { MdAirportShuttle } from "react-icons/md";
import { MdAirlineSeatIndividualSuite } from "react-icons/md";
import { MdApartment } from "react-icons/md";
import { MdBeachAccess } from "react-icons/md";
import { MdBed } from "react-icons/md";
import { MdCabin } from "react-icons/md";
import { MdCastle } from "react-icons/md";
import { MdGroups } from "react-icons/md";

const styles = {
  container: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: '10px',
    backgroundColor: '#ffffff',
    marginBottom: '20px',
    marginTop: '30px',
  },
  filterItem: {
    flex: 1,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    textAlign: 'center',
  },
  icon: {
    width: '35px',
    height: '35px',
    cursor: 'pointer',
  },
  label: {
    marginTop: '5px'
  },
};

const IconWrapper = styled.div`
  ${props => props.hover && css`
    transform: scale(1.1); /* hover 시 조금 커지는 효과 */
  `}
`;

const filters = [
  { icon: <MdAirlineSeatIndividualSuite style={styles.icon}/>, label: '모텔' },
  { icon: <MdBed style={styles.icon}/>, label: '호텔' },
  { icon: <MdBeachAccess style={styles.icon}/>, label: '콘도' },
  { icon: <MdGroups style={styles.icon}/>, label: '게스트하우스' },
  { icon: <MdAirportShuttle style={styles.icon}/>, label: '민박' },
  { icon: <MdApartment style={styles.icon}/>, label: '아파트' },
  { icon: <MdCastle style={styles.icon}/>, label: '펜션' },
  { icon: <MdCabin style={styles.icon}/>, label: '빌라' },
];

const FilterBar = () => {
  return (
    <div style={styles.container}>
      {filters.map((filter, index) => (
        <IconWrapper key={index} style={styles.filterItem} hover>
          {filter.icon}
          <span style={styles.label}>{filter.label}</span>
        </IconWrapper>
      ))}
    </div>
  );
};

export default FilterBar;
