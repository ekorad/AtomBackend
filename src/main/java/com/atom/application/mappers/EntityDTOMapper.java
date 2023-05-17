package com.atom.application.mappers;

/**
 * An interface implemented by mapper objects which perform mappings between
 * entities and DTOs.
 * <p>
 * Entity to DTO mappings are performed by <i>facade</i> services through the
 * use of a mapper object.
 * <p>
 * The mappings are performed in order to minimize the quantity of information
 * sent over individual HTTP transfers. As such, <i>DTOs</i> (<b>D</b>ata
 * <b>T</b>ransfer <b>O</b>bjects) store a minimal set of properties from the
 * corresponding entity type.
 * 
 * @param <E> the type of the entity
 * @param <D> the type of the DTO
 */
public interface EntityDTOMapper<E, D> {

    /**
     * Maps a given entity to the corresponding DTO.
     * 
     * @param entity - the entity to be mapped to a DTO
     * @return the resulting DTO
     */
    public D mapToDto(E entity);

    /**
     * Maps a given DTO to the corresponding entity
     * 
     * @param dto - the DTO to be mapped to an entity
     * @return the resulting entity
     */
    public E mapToEntity(D dto);

}
